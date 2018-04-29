package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {
    @BindView(R.id.steps_rv)
    RecyclerView stepsRecyclerView;

    private static final String CLASS_NAME = "IngredientsFragment";
    private Unbinder unbinder;
    //    private RecipesViewModel recipesViewModel;
    private StepsAdapter stepsAdapter;
    private ArrayList<Step> steps;

    public StepsFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate");
//        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        if (getArguments().containsKey("StepsForSingleRecipe")) {
            steps = getArguments().getParcelable("StepsForSingleRecipe");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SHDebug.debugTag(CLASS_NAME, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);


        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsAdapter = new StepsAdapter();
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setSteps(steps);

/*
        recipesViewModel.getRecipe().observe(this, steps -> {
            stepsAdapter.setSteps(steps.getSteps());
        });
*/

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }
}
