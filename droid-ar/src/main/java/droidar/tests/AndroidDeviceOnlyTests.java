package droidar.tests;

import android.content.Context;

import droidar.util.Calculus;

public class AndroidDeviceOnlyTests extends SimpleTesting {

	private Context myContext;

	public AndroidDeviceOnlyTests(Context c) {
		myContext = c;
	}

	@Override
	public void run() throws Exception {
		calcerTests();
	}

	private void calcerTests() throws Exception {
		Calculus.calculateTermResult(myContext, "7+7",
				new Calculus.TermResultListener() {

					@Override
					public void returnResult(String result) {
						try {
							System.out.println("result should be 14 and is "
									+ result);
							assertTrue(Integer.getInteger(result) == 14);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}
}
