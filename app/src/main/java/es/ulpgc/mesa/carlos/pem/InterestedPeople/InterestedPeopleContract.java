package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface InterestedPeopleContract {

    interface View {
        void displayInterestedPeople(InterestedPeopleViewModel viewModel);

        void injectPresenter(Presenter presenter);

        void displayData(InterestedPeopleViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void fillLikesArray();
    }

    interface Model {
        String fetchData();

        void fillInterestedPeopleArray(Contract.FillInterestedPeopleArray fillInterestedPeopleArray);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(InterestedPeopleState state);

        InterestedPeopleState getDataFromPreviousScreen();
    }
}
