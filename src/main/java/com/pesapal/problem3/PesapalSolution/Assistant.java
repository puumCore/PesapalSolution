package com.pesapal.problem3.PesapalSolution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.IntStream;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = "https://github.com/puumCore">GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 19/01/2023
 */

@Service
@Slf4j
public class Assistant implements Functions {

    protected final Clock clock = Clock.system(ZoneId.of("Africa/Nairobi"));

    /**
     * This function adds a client name into the queue while ensuring that there are no duplicates of the same client name in the queue.
     *
     * @param name The client to add
     * @return True if the client was successfully added.
     */
    @Override
    public boolean add_client(String name) {
        if (CLIENT_QUEUE.stream().noneMatch(s -> s.equals(name))) {
            if (CLIENT_QUEUE.offer(name)) {
                ONLINE_CLIENTS.put(name, LocalDateTime.now(clock));
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * This function deletes a client from the queue
     *
     * @param name The client to delete
     * @return True if the client was successfully removed
     */
    @Override
    public boolean remove_client(String name) {
        if (CLIENT_QUEUE.remove(name)) {
            ONLINE_CLIENTS.remove(name);
            return true;
        }
        return false;
    }

    /**
     * This function distributes & executes the command provided by the client among the subordinate clients
     *
     * @param client  The client who has provided the command
     * @param command What the lower ranked clients should execute
     * @return True if conditions to execute command have been met
     */
    @Override
    public boolean execute(String client, String command) {
        var clientRank = CLIENT_QUEUE.stream().toList().indexOf(client);
        if ((clientRank - (CLIENT_QUEUE.size() - 1)) < 0) {
            IntStream.range(0, CLIENT_QUEUE.size())
                    .filter(rank -> rank > clientRank)
                    .forEach(rank -> log.info("Client '{}'\t|\tExecuting the command '{}' on behalf of the superior client '{}'.", CLIENT_QUEUE.stream().toList().get(rank), command, client));
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function updates the list of online clients who have been inactive for over 60 seconds
     *
     * @return The runnable thread with instructions to remove clients.
     */
    @Override
    public Runnable update_online_clients() {
        return () -> {
            CLIENT_QUEUE.forEach(client -> {
                var localDateTime = ONLINE_CLIENTS.get(client);
                if (localDateTime != null) {
                    var duration = Duration.between(localDateTime, LocalDateTime.now(clock));
                    log.info("Client '{}' was connected {} seconds ago", client, duration.getSeconds());
                    if (duration.getSeconds() >= 60) {
                        if (remove_client(client)) {
                            log.info("Client '{}' is disconnected", client);
                        }
                    }
                }
            });
            log.info("'{}' online clients found", (long) ONLINE_CLIENTS.size());
        };
    }

    /**
     * When the client periodically pings the system, their last seen time is updated
     *
     * @param client The client whose last seen time is to be updated
     * @return True if the client's last seen time was successfully updated
     */
    @Override
    public boolean update_client_online_status(String client) {
        return ONLINE_CLIENTS.put(client, LocalDateTime.now(clock)) != null;
    }

}
