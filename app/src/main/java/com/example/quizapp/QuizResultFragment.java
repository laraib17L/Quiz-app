package com.example.quizapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizResultFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    public QuizResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_quiz_result_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        TextView tx= view.findViewById(R.id.total_score_textview);

        databaseHelper = new DatabaseHelper(requireContext());

        List<QuizResult> quizResults = databaseHelper.getAllQuizResults();
        if (quizResults.isEmpty()) {
            Toast.makeText(requireContext(), "No quiz results available.", Toast.LENGTH_SHORT).show();
        } else {
            QuizResultAdapter quizResultAdapter = new QuizResultAdapter(requireContext(), quizResults);
            recyclerView.setAdapter(quizResultAdapter);
            int totalScore = databaseHelper.getTotalScore();
            tx.setText("Total Score: " + totalScore);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        databaseHelper.close();
    }
}
