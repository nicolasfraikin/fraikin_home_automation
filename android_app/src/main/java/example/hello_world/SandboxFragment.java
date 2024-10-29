package example.hello_world;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class SandboxFragment extends Fragment {

    public SandboxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sandbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button clickMeButton = getView().findViewById(R.id.clickMeButton);
        TextView helloBazelTextView = getView().findViewById(R.id.helloBazelTextView);

        Greeter greeter = new Greeter();

        // Bazel supports Java 8 language features like lambdas!
        clickMeButton.setOnClickListener(v -> helloBazelTextView.setText(greeter.sayHello()));
    }
}
