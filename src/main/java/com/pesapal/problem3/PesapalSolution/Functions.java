package com.pesapal.problem3.PesapalSolution;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = "https://github.com/puumCore">GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 19/01/2023
 */

public interface Functions {

    /**
     * Queues are a linear data structure whose operations are performed in FIFO(First In First Out) order.<br>
     * The queue is meant to be universal across its dependants.
     */
    Queue<String> CLIENT_QUEUE = new ConcurrentLinkedQueue<>();
    /**
     * This defines the maximum number of clients that can be accepted.
     */
    int maxClients = 10;
    HashMap<String, LocalDateTime> ONLINE_CLIENTS = new HashMap<>();

    /**
     * This function adds a client name into the queue while checking if thr maximum number of clients has been reached and<br>
     * ensuring that there are no duplicates of the same client name in the queue.
     *
     * @param name The client to add
     * @return True if the client was successfully added.
     */
    boolean add_client(String name);

    /**
     * This function deletes a client from the queue
     *
     * @param name The client to delete
     * @return True if the client was successfully removed
     */
    boolean remove_client(String name);

    /**
     * This function distributes & executes the command provided by the client among the subordinate clients
     *
     * @param client  The client who has provided the command
     * @param command What the lower ranked clients should execute
     * @return True if conditions to execute command have been met
     */
    boolean execute(String client, String command);

    /**
     * This function updates the list of online clients who have been inactive for over 60 seconds
     *
     * @return The runnable thread with instructions to remove clients.
     */
    Runnable update_online_clients();

    /**
     * When the client periodically pings the system, their last seen time is updated
     *
     * @param client The client whose last seen time is to be updated
     * @return True if the client's last seen time was successfully updated
     */
    boolean update_client_online_status(String client);


}
