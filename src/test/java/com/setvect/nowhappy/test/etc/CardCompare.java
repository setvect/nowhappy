package com.setvect.nowhappy.test.etc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class CardCompare {
	public static void main(String[] args) throws IOException {
		List<String> myList = FileUtils.readLines(new File("temp/가계부.txt"));
		List<String> myCard = FileUtils.readLines(new File("temp/카드.txt"));

		Map<String, String> myListMap = extracted(myList);
		Map<String, String> myCardMap = extracted(myCard);
		for (Entry<String, String> entry : myCardMap.entrySet()) {
			String cardKey = entry.getKey();
			String myValue = myListMap.get(cardKey);
			if (myValue == null) {
				System.out.println(entry.getValue());
			}
		}

		System.out.println("끝.");
	}

	private static Map<String, String> extracted(List<String> myList) {
		Map<String, String> myMap = new TreeMap<String, String>();
		for (String s : myList) {
			String[] tokens = s.split("\t");
			myMap.put(tokens[0].trim() + "_" + tokens[2].trim(), s);
		}
		return myMap;
	}
}
