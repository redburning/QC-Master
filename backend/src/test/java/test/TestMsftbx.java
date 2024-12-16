package test;

import java.nio.file.Path;
import java.nio.file.Paths;

import umich.ms.datatypes.scan.IScan;
import umich.ms.fileio.exceptions.FileParsingException;
import umich.ms.fileio.filetypes.mzxml.MZXMLFile;
import umich.ms.fileio.filetypes.mzxml.MZXMLIndex;

public class TestMsftbx {

	public static void testCase1() throws FileParsingException {
		Path path = Paths.get("F:\\MyProjects\\QC-Master\\RAW\\M1-1.mzXML");
		MZXMLFile source = new MZXMLFile(path.toString());
		MZXMLIndex index = source.fetchIndex();
		for (Integer scanNumRaw : index.getMapByRawNum().keySet()) {
			IScan scan = source.parseScan(scanNumRaw, true);
			System.out.println(scanNumRaw + " - " + scan.getSpectrum().getMaxIntMz() + " : " + scan.getRt());
			
		}
		source.close();
	}
	
	public static void main(String[] args) throws FileParsingException {
		testCase1();
	}

}
