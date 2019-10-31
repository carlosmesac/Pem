package es.ulpgc.mesa.carlos.pem.App;

import android.widget.ImageView;

import java.util.ArrayList;

public interface Contract {
    /**
     * Method that creates a new account with the values passed in the parameters
     * @param nombre
     * @param nombreUsuario
     * @param correo
     * @param direccion
     * @param contra
     * @param callback
     */
    void createAcc(final String nombre, final String nombreUsuario, final String correo, final String direccion, final String contra, final Contract.CreateAccountCallback callback);


    //Lo que se llama cuando se crea un usuario
    interface CreateAccountCallback{
        void onAccountCreated(boolean error);
    }

    /**
     * Method that logs in if the user is on the Firebase Authentication dB
     * @param email
     * @param password
     * @param callback
     */
    void signIn(String email, String password, Contract.OnSignInCallback callback);

    interface OnSignInCallback{
        void onSignIn(boolean error);
    }

    /**
     * Method that disconnects the current user
     * @param callback
     */
    void signOut(Contract.SignOutCallback callback);

    interface SignOutCallback{
        void signOut(boolean error);
    }

    /**
     * Method that checks if is there an user logged
     * @param isLoginCallBack
     */
    void isLogin(Contract.IsLoginCallBack isLoginCallBack);


    interface IsLoginCallBack{
        void isLogin(boolean error);
    }

    /**
     *Method that add the data values to the database and storage the image into firebase Storage
     * @param isbn
     * @param author
     * @param title
     * @param imageView
     * @param callback
     */
    void addBook(final String isbn, final String author, final String title, final ImageView imageView, final Contract.CreateBookEntryCallBack callback);

    interface CreateBookEntryCallBack {
        void onAddNewBook(boolean error);
    }

    /**
     * MEthod that fills an array with all the books in the firebase database
     * @param callback
     * @return array filled with all the books
     */
    ArrayList<BookItem> fillBooksArray(Contract.FillBooksArray callback);

    interface FillBooksArray{
        void onFillBooksArray(boolean error, ArrayList<BookItem> bookItemArrayList);
    }
    /**
     * MEthod that fills an array with all the books that the user liked in the firebase database
     * @param callback
     * @return array filled with all the books
     */
    ArrayList<BookItem> fillInterestedBooksArray(Contract.FillInterestedBooksArray callback);

    interface FillInterestedBooksArray{
        void onFillInterestedBooksArray(boolean error, ArrayList<BookItem> bookItemArrayList);
    }

    ArrayList<Like> fillInterestedPeopleArray(Contract.FillInterestedPeopleArray callback);

    interface FillInterestedPeopleArray{
        void onFillInterestedPeopleArray(boolean error, ArrayList<Like> likeArrayList);
    }

}
