package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class InterestedPeopleModel implements InterestedPeopleContract.Model {

    public static String TAG = InterestedPeopleModel.class.getSimpleName();
    private Contract contract;
    public InterestedPeopleModel(Contract contract) {
    this.contract=contract;
    }

    @Override
    public String fetchData() {
        // Log.e(TAG, "fetchData()");
        return "Hello";
    }

    @Override
    public void fillInterestedPeopleArray(Contract.FillInterestedPeopleArray fillInterestedPeopleArray) {
        contract.fillInterestedPeopleArray(fillInterestedPeopleArray);
    }
}
