package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class InterestedBooksModel implements InterestedBooksContract.Model {

    public static String TAG = InterestedBooksModel.class.getSimpleName();
    private Contract repository;

    public InterestedBooksModel(Contract contract) {
        this.repository= contract;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    @Override
    public void fillInterestedBooksArray(Contract.FillInterestedBooksArray fillInterestedBooksArray) {
        repository.fillInterestedBooksArray(fillInterestedBooksArray);
    }
}
