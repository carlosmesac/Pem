package es.ulpgc.mesa.carlos.pem.MyBooks;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.mesa.carlos.pem.AddBook.AddBookActivity;
import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.Login.LoginActivity;

public class MyBooksRouter implements MyBooksContract.Router {

    public static String TAG = MyBooksRouter.class.getSimpleName();

    private AppMediator mediator;

    public MyBooksRouter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void navigateToNextScreen() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, MyBooksActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void passDataToNextScreen(MyBooksState state) {
        mediator.setMyBooksState(state);
    }

    @Override
    public MyBooksState getDataFromPreviousScreen() {
        MyBooksState state = mediator.getMyBooksState();
        return state;
    }

    @Override
    public void addBook() {
        Context context = mediator.getApplicationContext();
        Intent intent = new Intent(context, AddBookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
