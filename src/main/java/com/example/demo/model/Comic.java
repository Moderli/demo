package com.example.demo.model;

import java.util.List;

public class Comic {
    private String title;
    private String description;
    private String imageUrl;  // The full image URL
    private String comiclink; // Specific URL for the "comiclink"
    private List<Url> urls;   // List to store all URLs
    private Thumbnail thumbnail; // Thumbnail object

    // Getters and setters for all fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String path, String extension) {
        this.imageUrl = path + "." + extension;  // Concatenate path and extension to form the image URL
    }

    public String getComiclink() {
        return comiclink;
    }

    public void setComiclink(String comiclink) {
        this.comiclink = comiclink;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    // Nested classes for Url and Thumbnail
    public static class Url {
        private String type;
        private String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Thumbnail {
        private String path;
        private String extension;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }
}
