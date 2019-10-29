package es.ulpgc.mesa.carlos.pem.InterestedBooks;

public class InterestedBooksModel implements InterestedBooksContract.Model {

    public static String TAG = InterestedBooksModel.class.getSimpleName();

    public InterestedBooksModel() {

    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }
}
