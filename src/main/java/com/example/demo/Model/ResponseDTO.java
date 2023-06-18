package com.example.demo.Model;


public class ResponseDTO { // Usare esta clase para mandar los ShortLinks y la caducidad al usuario. (GET)
    private String originalUrl;
    private String shortLink;
    private String expirationDate;


    public ResponseDTO(String originalUrl, String shortLink, String expirationDate) {
        this.originalUrl = originalUrl; this.shortLink = shortLink;
        this.expirationDate = expirationDate;
    }
    public ResponseDTO(){} // if caso null -> error


    //Get Set
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortLink() { return shortLink; }
    public void setShortLink(String shortLink) { this.shortLink = shortLink; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }


    @Override
    public String toString() {
        return  "OriginalUrl: " + originalUrl + "\n" +
                "ShortUrl: " + "localhost:8080/" + shortLink + "\n" +
                "ExpirationDate: " + expirationDate ;
    }
}
