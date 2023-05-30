package com.example.demo.Model;
import java.time.LocalDateTime;


public class ResponseDTO { // Usare esta clase para mandar los ShortLinks y la caducidad al usuario. (GET)
    private String title;
    private String originalUrl;
    private String shortLink;
    private LocalDateTime expirationDate;


    public ResponseDTO(String title, String originalUrl, String shortLink, LocalDateTime expirationDate) {
        this.title = title;
        this.originalUrl = originalUrl; this.shortLink = shortLink;
        this.expirationDate = expirationDate;
    }
    public ResponseDTO(){} // if caso null -> error


    //Get Set
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortLink() { return shortLink; }
    public void setShortLink(String shortLink) { this.shortLink = shortLink; }

    public LocalDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }


    @Override
    public String toString() {
        return  "Title: " + title + "\n" +
                "OriginalUrl: " + originalUrl + "\n" +
                "ShortUrl: " + "localhost:8080/" + shortLink + "\n" +
                "ExpirationDate: " + expirationDate ;
    }
}
