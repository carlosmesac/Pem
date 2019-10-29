package es.ulpgc.mesa.carlos.pem.MyBooks;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Contract;

public class MyBooksModel implements MyBooksContract.Model {

    public static String TAG = MyBooksModel.class.getSimpleName();
    private Contract repository;
    public MyBooksModel(Contract repository) {
        this.repository= repository;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    @Override
    public ArrayList<BookItem> fillBooksArray(Contract.FillBooksArray callback) {
        return repository.fillBooksArray(callback);
    }
}
