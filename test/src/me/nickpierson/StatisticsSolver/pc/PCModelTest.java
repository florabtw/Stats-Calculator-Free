package me.nickpierson.StatisticsSolver.pc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PCModelTest {

	private PCModel model;
	private DataActionListener listenerN;
	private DataActionListener listenerR;
	private DataActionListener listenerNAndR;
	private DataActionListener listenerNAndNs;
	private DataActionListener listenerAll;

	private String testN;
	private String testR;
	private String testNs;
	private ArrayList<Integer> nVals;
	private HashMap<Enum<?>, Integer> mapN;
	private HashMap<Enum<?>, Integer> mapR;
	private HashMap<Enum<?>, Integer> mapNAndR;
	private HashMap<Enum<?>, Object> mapNAndNs;
	private HashMap<Enum<?>, Object> mapAll;

	@Before
	public void setup() {
		model = new PCModel();

		testN = "20";
		testR = "10";
		testNs = "3,2";

		nVals = new ArrayList<Integer>();
		nVals.add(3);
		nVals.add(2);

		mapN = new HashMap<Enum<?>, Integer>();
		mapR = new HashMap<Enum<?>, Integer>();
		mapNAndR = new HashMap<Enum<?>, Integer>();
		mapNAndNs = new HashMap<Enum<?>, Object>();
		mapAll = new HashMap<Enum<?>, Object>();

		mapN.put(PCModel.Keys.N_VALUE, 20);
		mapR.put(PCModel.Keys.R_VALUE, 10);
		mapNAndR.put(PCModel.Keys.N_VALUE, 20);
		mapNAndR.put(PCModel.Keys.R_VALUE, 10);
		mapNAndNs.put(PCModel.Keys.N_VALUE, 20);
		mapNAndNs.put(PCModel.Keys.N_VALUES, nVals);
		mapAll.put(PCModel.Keys.N_VALUE, 20);
		mapAll.put(PCModel.Keys.R_VALUE, 10);
		mapAll.put(PCModel.Keys.N_VALUES, nVals);

		listenerN = mock(DataActionListener.class);
		listenerR = mock(DataActionListener.class);
		listenerNAndR = mock(DataActionListener.class);
		listenerNAndNs = mock(DataActionListener.class);
		listenerAll = mock(DataActionListener.class);
	}

	@Test
	public void validateInputNotifiesPresenter_IfValueOverOneThousandIsEntered() {
		ActionListener listener = mock(ActionListener.class);
		model.addListener(listener, PCModel.Types.INPUT_OVER_MAX_VALUE);

		model.validateInput("1001", "", "");
		model.validateInput("", "1001", "");
		model.validateInput("1001", "1001", "");

		verify(listener, times(6)).fire();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputHandlesInputCorrectly_ForNoValidValues() {
		addAllListeners();

		model.validateInput("", "", "");
		model.validateInput("", "", "3,4");
		model.validateInput("1001", "", "");
		model.validateInput("", "", "2147483657,5,20");
		model.validateInput("", "2147483657", "");
		model.validateInput("52147483657", "", "");

		verify(listenerN, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndNs, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerAll, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputHandlesInputCorrectly_ForOnlyValidNValue() {
		addAllListeners();

		model.validateInput(testN, "", "");
		model.validateInput(testN, "", "10,11");

		verify(listenerN, times(2)).fire(mapN);

		verify(listenerR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndNs, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerAll, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputHandlesInputCorrectly_ForOnlyValidRValue() {
		addAllListeners();

		model.validateInput("", testR, "");
		model.validateInput("", testR, "3,4");

		verify(listenerR, times(2)).fire(mapR);

		verify(listenerN, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndNs, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerAll, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputHandlesInputCorrectly_ForNAndRValues() {
		addAllListeners();

		model.validateInput(testN, testR, "");

		verify(listenerNAndR).fire(mapNAndR);
		verify(listenerN, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndNs, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerAll, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputHandlesInputCorrectly_ForNAndNValues() {
		addAllListeners();

		model.validateInput(testN, "", testNs);
		model.validateInput(testN, "", "3,,,,,,2,");

		verify(listenerNAndNs, times(2)).fire(mapNAndNs);

		verify(listenerN, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerAll, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputHandlesInputCorrectly_ForAllValues() {
		addAllListeners();

		model.validateInput(testN, testR, testNs);

		verify(listenerAll).fire(mapAll);
		verify(listenerN, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndR, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(listenerNAndNs, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	private void addAllListeners() {
		model.addListener(listenerN, PCModel.Types.ONLY_VALID_N);
		model.addListener(listenerR, PCModel.Types.ONLY_VALID_R);
		model.addListener(listenerNAndR, PCModel.Types.VALID_N_AND_R);
		model.addListener(listenerNAndNs, PCModel.Types.VALID_N_AND_NS);
		model.addListener(listenerAll, PCModel.Types.ALL_VALUES_VALID);
	}

	@Test
	public void factorialReturnsCorrectValue() {
		assertEquals(BigInteger.valueOf(120), model.calculateFact(5));
		assertEquals(BigInteger.valueOf(479001600), model.calculateFact(12));
		assertEquals(BigInteger.ONE, model.calculateFact(1));
		assertEquals(BigInteger.ONE, model.calculateFact(0));
		assertEquals(BigInteger.ZERO, model.calculateFact(-1));
	}

	@Test
	public void permutationReturnCorrectValue() {
		assertEquals(BigInteger.valueOf(60), model.calculatePermutation(5, 3));
		assertEquals(BigInteger.valueOf(151200), model.calculatePermutation(10, 6));
		assertEquals(BigInteger.valueOf(5040), model.calculatePermutation(7, 7));
		assertEquals(BigInteger.valueOf(5), model.calculatePermutation(5, 1));
		assertEquals(BigInteger.ONE, model.calculatePermutation(6, 0));
		assertEquals(BigInteger.ZERO, model.calculatePermutation(3, 4));
	}

	@Test
	public void combinationReturnsCorrectValue() {
		assertEquals(BigInteger.valueOf(10), model.calculateCombination(5, 3));
		assertEquals(BigInteger.valueOf(210), model.calculateCombination(10, 6));
		assertEquals(BigInteger.ONE, model.calculateCombination(7, 7));
		assertEquals(BigInteger.valueOf(5), model.calculateCombination(5, 1));
		assertEquals(BigInteger.ONE, model.calculateCombination(6, 0));
		assertEquals(BigInteger.ZERO, model.calculateCombination(3, 4));
	}

	@Test
	public void indistinctPermutationReturnsCorrectValue() {
		assertEquals(BigInteger.valueOf(60), model.calculateIndistinct(6, makeArrayList(3, 2)));
		assertEquals(BigInteger.valueOf(2), model.calculateIndistinct(2, makeArrayList(1, 1)));
		assertEquals(BigInteger.ONE, model.calculateIndistinct(1, makeArrayList(1)));
		assertEquals(BigInteger.valueOf(120), model.calculateIndistinct(5, makeArrayList(0)));
	}

	public ArrayList<Integer> makeArrayList(int... values) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int val : values) {
			list.add(val);
		}
		return list;
	}
}
