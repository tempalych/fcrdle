package com.tempalych.fcrdle.wikidata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class WikidataResponse {
    private Head head;
    private Results results;

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    public static class Head {
        private List<String> vars;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    public static class Results {
        private List<Binding> bindings;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    public static class Binding {
        private Literal club;
        private Literal capacity;
        private Literal coordinates;
        private Literal leagueLabel;
        private Literal clubLabel;
        private Literal stadiumLabel;
        private Literal seasonLabel;
        private Literal cityLabel;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    public static class Literal {
        private String datatype;
        private String type;
        private String value;
        private String xmlLang;
    }
}
