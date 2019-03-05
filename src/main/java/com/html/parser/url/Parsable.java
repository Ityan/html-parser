package com.html.parser.url;

import com.html.parser.Node;

import java.io.IOException;
import java.util.List;

public interface Parsable {

    List<Node> parse() throws IOException;

    class Fork extends Wrap {

        public Fork(String site) {
            super(fork(site));
        }

        private static Parsable fork(String site) {
            if (site.startsWith(Navidom.SITE)) {
                return new Navidom(site);
            }

            throw new IllegalStateException(String.format("URL [%s] not supported.", site));
        }
    }

    class Wrap implements Parsable {

        private final Parsable parsable;

        public Wrap(Parsable parsable) {
            this.parsable = parsable;
        }

        @Override
        public List<Node> parse() throws IOException {
            return this.parsable.parse();
        }
    }

}
