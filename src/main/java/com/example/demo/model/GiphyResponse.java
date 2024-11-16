package com.example.demo.model;

import java.util.List;

public class GiphyResponse {
    private List<StickerData> data;

    public List<StickerData> getData() {
        return data;
    }

    public void setData(List<StickerData> data) {
        this.data = data;
    }

    public static class StickerData {
        private Images images;

        public Images getImages() {
            return images;
        }

        public void setImages(Images images) {
            this.images = images;
        }
    }

    public static class Images {
        private Original original;

        public Original getOriginal() {
            return original;
        }

        public void setOriginal(Original original) {
            this.original = original;
        }
    }

    public static class Original {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
