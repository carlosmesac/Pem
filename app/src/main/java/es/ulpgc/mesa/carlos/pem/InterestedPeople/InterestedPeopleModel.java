package es.ulpgc.mesa.carlos.pem.InterestedPeople;

public class InterestedPeopleModel implements InterestedPeopleContract.Model {

    public static String TAG = InterestedPeopleModel.class.getSimpleName();

    public InterestedPeopleModel() {

    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }
}
