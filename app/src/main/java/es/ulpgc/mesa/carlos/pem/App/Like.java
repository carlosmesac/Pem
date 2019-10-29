package es.ulpgc.mesa.carlos.pem.App;

public class Like {
    private String publisher,title,currentUser;

    public Like(String publisher, String title, String currentUser) {
        this.publisher = publisher;
        this.title = title;
        this.currentUser = currentUser;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
