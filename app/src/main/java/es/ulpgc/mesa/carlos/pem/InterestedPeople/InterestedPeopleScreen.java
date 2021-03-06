package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.App.Repository;

public class InterestedPeopleScreen {

    public static void configure(InterestedPeopleContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        InterestedPeopleState state = mediator.getInterestedPeopleState();

        InterestedPeopleContract.Router router = new InterestedPeopleRouter(mediator);

        Contract contract= Repository.getInstance(context.get());
        InterestedPeopleContract.Presenter presenter = new InterestedPeoplePresenter(state);
        InterestedPeopleContract.Model model = new InterestedPeopleModel(contract);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
