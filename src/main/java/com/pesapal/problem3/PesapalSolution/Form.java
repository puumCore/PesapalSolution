package com.pesapal.problem3.PesapalSolution;

import lombok.NonNull;

import java.util.StringJoiner;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = "https://github.com/puumCore">GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 20/01/2023
 */

public abstract class Form {

    public record User(@org.springframework.lang.NonNull String client) {

        @Override
        public String toString() {
            return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                    .add("client='" + client + "'")
                    .toString();
        }
    }

    public record Bidding(@NonNull String client, @NonNull String cmd) {

        @Override
        public String toString() {
            return new StringJoiner(", ", Bidding.class.getSimpleName() + "[", "]")
                    .add("client='" + client + "'")
                    .add("cmd='" + cmd + "'")
                    .toString();
        }
    }

}
