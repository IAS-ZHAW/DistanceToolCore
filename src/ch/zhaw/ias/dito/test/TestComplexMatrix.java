package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.ManhattanDist;

public class TestComplexMatrix extends TestCase {
	public void testReadFromFile() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m3x3.csv"), ';');
		assertEquals(m, new Matrix(new double[][] {{4.0, 5.0, 667}, {4.5, 30.25, 3}, {1.2, Double.NaN, 100.01}}));
	}
	
	public void testLargeManhattan() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m10x10mul.csv"), ';');
		assertEquals(m.transpose().calculateDistance(new ManhattanDist()), Matrix.readFromFile(new File("./testdata/m10x10mul-manhattan.csv"), ';'));
		
		m = Matrix.readFromFile(new File("./testdata/m10x10add.csv"), ';');
		assertEquals(m.transpose().calculateDistance(new ManhattanDist()), Matrix.readFromFile(new File("./testdata/m10x10add-manhattan.csv"), ';'));		
	}	
}
