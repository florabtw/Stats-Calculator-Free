package me.nickpierson.StatsCalculator.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BasicModelTest {

	public BasicModel model;
	private double DELTA = .000001;

	private DataActionListener validDataListener;
	private DataActionListener invalidDataListener;
	private ActionListener validListener;
	private ActionListener invalidListener;
	private Activity activity;

	@Before
	public void setup() {
		activity = mock(Activity.class);
		model = new BasicModel(activity);

		validDataListener = mock(DataActionListener.class);
		invalidDataListener = mock(DataActionListener.class);
		validListener = mock(ActionListener.class);
		invalidListener = mock(ActionListener.class);
	}

	@Test
	public void modelReturnsEmptyHashMapOnRequest() {
		double[] emptyMap = model.getEmptyResults();

		for (int i = 0; i < emptyMap.length; i++) {
			assertTrue(Double.isNaN(emptyMap[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputNotifiesCorrectly_GivenValidInput() {
		HashMap<Enum<?>, ArrayList<Double>> validMap1 = new HashMap<Enum<?>, ArrayList<Double>>();
		HashMap<Enum<?>, ArrayList<Double>> validMap2 = new HashMap<Enum<?>, ArrayList<Double>>();
		String validInput1 = "500,30x3,59.0233";
		String validInput2 = "55.5,,31.3x2,-3,.2,-.23";
		String validInput3 = "60.1123x100000";
		ArrayList<Double> validList1 = makeValidList(500, 30, 30, 30, 59.0233);
		ArrayList<Double> validList2 = makeValidList(55.5, 31.3, 31.3, -3, .2, -.23);
		validMap1.put(BasicModel.Keys.VALIDATED_LIST, validList1);
		validMap2.put(BasicModel.Keys.VALIDATED_LIST, validList2);
		addAllListeners();

		model.validateInput(validInput1);
		model.validateInput(validInput2);
		model.validateInput(validInput3);

		verify(validDataListener).fire(validMap1);
		verify(validDataListener).fire(validMap2);
		verify(validDataListener, times(3)).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(invalidDataListener, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputNotifiesCorrectly_GivenInvalidInput() {
		HashMap<Enum<?>, Object> invalidMapFirstPos = new HashMap<Enum<?>, Object>();
		HashMap<Enum<?>, Object> invalidMapThirdPos = new HashMap<Enum<?>, Object>();
		invalidMapFirstPos.put(BasicModel.Keys.INVALID_POSITION, 1);
		invalidMapThirdPos.put(BasicModel.Keys.INVALID_POSITION, 3);
		addAllListeners();

		verifyInvalidInput(",,", invalidMapFirstPos, "");

		verifyInvalidInput("23x1.5", invalidMapFirstPos, "23x1.5");

		verifyInvalidInput("23x100001", invalidMapFirstPos, "23x100001");

		verifyInvalidInput("x5,27", invalidMapFirstPos, "x5");

		verifyInvalidInput("5x,27", invalidMapFirstPos, "5x");

		verifyInvalidInput("23,52x2,56..8,9", invalidMapThirdPos, "56..8");

		verifyInvalidInput("23,25.6,23..5x2,7", invalidMapThirdPos, "23..5x2");

		verifyInvalidInput("23,25.6,25-5,7", invalidMapThirdPos, "25-5");

		verifyInvalidInput("23,25.6,25xx5,7", invalidMapThirdPos, "25xx5");

		verify(validDataListener, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	public void verifyInvalidInput(String invalidInput, HashMap<Enum<?>, Object> invalidMap, String invalidText) {
		model.validateInput(invalidInput);
		invalidMap.put(BasicModel.Keys.INVALID_TEXT, invalidText);
		verify(invalidDataListener).fire(invalidMap);
	}

	public void addAllListeners() {
		model.addListener(validDataListener, BasicModel.Types.VALID_INPUT);
		model.addListener(invalidDataListener, BasicModel.Types.INVALID_INPUT);
	}

	@Test
	public void calculateResults_CalculatesCorrectResult() {
		ArrayList<Double> sampleInput = makeValidList(45, 68.1, 29.4, -54, -.19, 3.0001);

		double[] actualResults = model.calculateResults(sampleInput);

		assertEquals(6.0, actualResults[0], DELTA);
		assertEquals(91.3101, actualResults[1], DELTA);
		assertEquals(15.21835, actualResults[2], DELTA);
		assertEquals(Double.NaN, actualResults[3], DELTA);
		assertEquals(16.20005, actualResults[4], DELTA);
		assertEquals(Double.NaN, actualResults[5], DELTA);
		assertEquals(122.1, actualResults[6], DELTA);
		assertEquals(1812.483527, actualResults[7], DELTA);
		assertEquals(1510.402939, actualResults[8], DELTA);
		assertEquals(42.573272, actualResults[9], DELTA);
		assertEquals(38.863902, actualResults[10], DELTA);
		assertEquals(2.797495, actualResults[11], DELTA);
		assertEquals(-.4542037, actualResults[12], DELTA);
		assertEquals(2.314556, actualResults[13], DELTA);

		ArrayList<Double> sampleInput1 = makeValidList(45, 68.1, 29.4, 54, 5.3, 5.3);
		double[] actualResult1 = model.calculateResults(sampleInput1);
		assertEquals(22.695621, actualResult1[3], DELTA);
		assertEquals(5.3, actualResult1[5], DELTA);

		ArrayList<Double> sampleInput2 = makeValidList(-99.5, -55, -32.2);
		double[] actualResult2 = model.calculateResults(sampleInput2);
		assertEquals(67.3, actualResult2[6], DELTA);
	}

	private ArrayList<Double> makeValidList(double... args) {
		ArrayList<Double> validList = new ArrayList<Double>();
		for (double val : args) {
			validList.add(val);
		}
		return validList;
	}

	@Test
	public void saveListNotifiesCorrectly_GivenValidInput() throws FileNotFoundException {
		addAllListListeners();
		String testName = "testName.txt";
		String input = "34x3,72.1,1337.H4CK3R";
		File testFile = new File(testName);

		model.saveList(testName, input);

		verify(validListener).fire();
		verify(invalidListener, never()).fire();

		testFile.delete();
	}

	@Test
	public void saveListNotifiesCorrectly_GivenInvalidInput() throws IOException {
		addAllListListeners();
		String testName = "someName.txt";
		String input = "OLLEH";
		File alreadyExists = new File(testName);
		alreadyExists.createNewFile();

		File testFile = new File(testName);
		FileOutputStream fakeStream = new FileOutputStream(testFile);
		when(activity.openFileOutput(testName, 0)).thenReturn(fakeStream);

		model.saveList(testName, input);

		verify(invalidListener).fire();
		verify(validListener, never()).fire();

		testFile.delete();
		alreadyExists.delete();
	}

	private void addAllListListeners() {
		model.addListener(validListener, BasicModel.Types.SAVE_SUCCESSFUL);
		model.addListener(invalidListener, BasicModel.Types.SAVE_FAILED);
	}

	@Test
	public void whenGetSavedLists_ThenSavedListsAreReturned() throws IOException {
		when(activity.getFilesDir()).thenReturn(new File("./testDir"));
		String listOne = "first";
		String listTwo = "second";
		File file1 = new File("testDir/" + listOne);
		File file2 = new File("testDir/" + listTwo);
		file1.createNewFile();
		file2.createNewFile();

		String[] lists = model.getSavedLists();

		assertTrue(lists.length == 2);

		/* file.list() lists in reverse order */
		assertEquals(lists[1], listOne);
		assertEquals(lists[0], listTwo);

		file1.delete();
		file2.delete();
	}

	@Test
	public void loadList_ReturnsCorrectListInput() {
		when(activity.getFilesDir()).thenReturn(new File("./testDir"));
		String listName = "someList";
		String expectedInput = "6,7,8,9";
		File listFile = new File("testDir/" + listName);
		makeList(listFile, expectedInput);
		model.addListener(invalidListener, BasicModel.Types.LOAD_ERROR);

		String realInput = model.loadList("someList");

		assertEquals(expectedInput, realInput);
		verify(invalidListener, never()).fire();

		listFile.delete();
	}

	@Test
	public void deleteList_DeletesListFromMemory() throws IOException {
		when(activity.getFilesDir()).thenReturn(new File("./testDir"));
		String listName = "someOtherList";
		File listFile = new File("testDir/" + listName);
		makeList(listFile, "any random input");
		model.addListener(invalidListener, BasicModel.Types.DELETE_ERROR);

		listFile.createNewFile();
		assertTrue(listFile.exists());

		model.deleteList(listName);

		verify(invalidListener, never()).fire();
		assertFalse(listFile.exists());
	}

	public void makeList(File file, String input) {
		FileOutputStream output;
		try {
			output = new FileOutputStream(file);
			output.write(input.getBytes());
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
