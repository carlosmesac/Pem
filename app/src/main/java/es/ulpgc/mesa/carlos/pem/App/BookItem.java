package es.ulpgc.mesa.carlos.pem.App;

public class BookItem {
    private String autor, image, title, isbn,user,correo;

    public BookItem() {
    }

    public BookItem(String autor, String image, String isbn, String title, String user,String correo){
        this.autor=autor;
        this.image=image;
        this.title=title;
        this.isbn=isbn;
        this.user=user;
        this.correo= correo;


    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCorreo() {
        return correo;
    }

    public void setEmail(String correo) {
        this.correo = correo;
    }
}
