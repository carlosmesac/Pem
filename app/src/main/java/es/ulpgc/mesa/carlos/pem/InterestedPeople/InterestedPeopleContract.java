package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import java.lang.ref.WeakReference;

public interface InterestedPeopleContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(InterestedPeopleViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();
    }

    interface Model {
        String fetchData();
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(InterestedPeopleState state);

        InterestedPeopleState getDataFromPreviousScreen();
    }
}
