package es.ulpgc.mesa.carlos.pem.App;

import android.app.Application;

import es.ulpgc.mesa.carlos.pem.AddBook.AddBookState;
import es.ulpgc.mesa.carlos.pem.Home.HomeState;
import es.ulpgc.mesa.carlos.pem.InterestedBooks.InterestedBooksState;
import es.ulpgc.mesa.carlos.pem.InterestedPeople.InterestedPeopleState;
import es.ulpgc.mesa.carlos.pem.Login.LoginState;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksState;
import es.ulpgc.mesa.carlos.pem.SignIn.SignInState;
import es.ulpgc.mesa.carlos.pem.Usuario.UserState;

public class AppMediator extends Application {
    private LoginState loginState;
    private SignInState signInState;
    private HomeState homeState;
    private AddBookState addBookState;
    private UserState userState;
    private MyBooksState myBooksState;
    private InterestedBooksState interestedBooksState;
    private InterestedPeopleState interestedPeopleState;


    public AppMediator() {
        loginState = new LoginState();
        signInState = new SignInState();
        homeState = new HomeState();
        addBookState= new AddBookState();
        userState= new UserState();
        myBooksState = new MyBooksState();
        interestedBooksState = new InterestedBooksState();
        interestedPeopleState = new InterestedPeopleState();
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public void setLoginState(LoginState loginState) {
        this.loginState = loginState;
    }

    public SignInState getSignInState() {
        return signInState;
    }

    public void setSignInState(SignInState signInState) {
        this.signInState = signInState;
    }

    public HomeState getHomeState() {
        return homeState;
    }

    public void setHomeState(HomeState homeState) {
        this.homeState = homeState;
    }

    public AddBookState getAddBookState() {
        return addBookState;
    }

    public void setAddBookState(AddBookState addBookState) {
        this.addBookState = addBookState;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public MyBooksState getMyBooksState() {
        return myBooksState;
    }

    public void setMyBooksState(MyBooksState booksState) {
        this.myBooksState = booksState;
    }

    public InterestedBooksState getInterestedBooksState() {
        return interestedBooksState;
    }

    public void setInterestedBooksState(InterestedBooksState interestedBooksState) {
        this.interestedBooksState = interestedBooksState;
    }

    public InterestedPeopleState getInterestedPeopleState() {
        return interestedPeopleState;
    }

    public void setInterestedPeopleState(InterestedPeopleState interestedPeopleState) {
        this.interestedPeopleState = interestedPeopleState;
    }
}
