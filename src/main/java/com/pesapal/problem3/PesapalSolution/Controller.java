package com.pesapal.problem3.PesapalSolution;

import com.pesapal.problem3.PesapalSolution._exception.BadRequestException;
import com.pesapal.problem3.PesapalSolution._exception.FailureException;
import com.pesapal.problem3.PesapalSolution._exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = "https://github.com/puumCore">GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 19/01/2023
 */

@RestController
@RequestMapping(path = "solution", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class Controller {

    @Autowired
    private Functions functions;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void accept_user(@RequestBody Form.User user) {
        log.info("body = {}", user);

        if (Functions.CLIENT_QUEUE.size() > Functions.maxClients) {
            throw new BadRequestException("Sorry but the maximum number of clients has been reached");
        }
        if (Functions.CLIENT_QUEUE.stream().anyMatch(s -> s.equals(user.client()))) {
            throw new BadRequestException("The desired client is already logged in the system");
        }

        if (!functions.add_client(user.client())) {
            throw new FailureException("Client name could not be added to queue");
        }
    }

    @GetMapping("/clients")
    Queue<String> get_clients() {
        return Functions.CLIENT_QUEUE;
    }

    @PostMapping(path = "/online", consumes = MediaType.APPLICATION_JSON_VALUE)
    void update_client_last_seen(@RequestBody Form.User user) {
        log.info("body = {}", user);

        if (Functions.CLIENT_QUEUE.stream().noneMatch(s -> s.equals(user.client()))) {
            throw new NotFoundException("The bidding client doesn't exist");
        }

        if (!functions.update_client_online_status(user.client())) {
            throw new FailureException("Could not update the client's last seen time");
        }
    }

    @PostMapping(path = "/cmd", consumes = MediaType.APPLICATION_JSON_VALUE)
    void carry_out_client_bidding(@RequestBody Form.Bidding bidding) {
        log.info("body = {}", bidding.toString());

        if (Functions.CLIENT_QUEUE.size() == 1) {
            throw new BadRequestException("Please add one more client to continue");
        }
        if (Functions.CLIENT_QUEUE.stream().noneMatch(s -> s.equals(bidding.client()))) {
            throw new BadRequestException("The commanding client doesn't exist");
        }

        if (!functions.execute(bidding.client(), bidding.cmd())) {
            throw new FailureException("The command has been rejected because there are no subordinate clients to execute your command");
        }
    }

}
