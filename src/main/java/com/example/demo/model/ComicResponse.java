package com.example.demo.model;

import java.util.List;

public class ComicResponse {
    private ComicData data;

    public ComicData getData() {
        return data;
    }

    public void setData(ComicData data) {
        this.data = data;
    }

    public static class ComicData {
        private List<Comic> results;

        public List<Comic> getResults() {
            return results;
        }

        public void setResults(List<Comic> results) {
            this.results = results;
        }
    }
}
