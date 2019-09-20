package com.devonfw.cobigen.pythonplugin.merger;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.devonfw.cobigen.api.exception.MergeException;

/**
 * Test methods for different python mergers of the plugin
 */
public class PythonMergerTest {

    /** Test resources root path */
    private static String testFileRootPath = "src/test/resources/testdata/unittest/files/";

    /**
     * Checks if the python-merger can be launched and if the output is correct with patchOverrides = false
     *
     * @test fails
     */
    @Test
    public void testMergingNoOverrides() {

        // arrange
        File baseFile = new File(testFileRootPath + "baseFile.py");

        // act
        String mergedContents =
            new PythonMerger("pythonmerge", false).merge(baseFile, readpythonFile("patchFile.py"), "UTF-8");

        assertThat(mergedContents).isNotNull();
        // Use the following assert as template for your testing
        // assertThat(mergedContents).contains("import { c, f } from 'd'");
        // assertThat(mergedContents).contains("private b: number;");
        // assertThat(mergedContents).contains("bProperty");

        mergedContents = new PythonMerger("pythonmerge", false).merge(baseFile, readpythonFile("patchFile.py"), "ISO-8859-1");

        assertThat(mergedContents).isNotNull();
        // Use the following assert as template for your testing
        // assertThat(mergedContents).contains("import { c, f } from 'd'");
        // assertThat(mergedContents).contains("private b: number;");
        // assertThat(mergedContents).contains("bProperty");

    }

    /**
     * Checks if the python-merger can be launched and if the output is correct with patchOverrides = true
     *
     * @test fails
     */
    @Test
    public void testMergingOverrides() {

        // arrange
        File baseFile = new File(testFileRootPath + "baseFile.py");

        // act
        String mergedContents =
            new PythonMerger("pythonmerge", true).merge(baseFile, readpythonFile("patchFile.py"), "UTF-8");

        assertThat(mergedContents).isNotNull();
        // Use the following assert as template for your testing
        // assertThat(mergedContents).contains("import { c, f } from 'd'");
        // assertThat(mergedContents).contains("private b: number;");
        // assertThat(mergedContents).contains("bProperty");

        mergedContents = new PythonMerger("pythonmerge", true).merge(baseFile, readpythonFile("patchFile.py"), "ISO-8859-1");

        assertThat(mergedContents).isNotNull();
        // Use the following assert as template for your testing
        // assertThat(mergedContents).contains("import { c, f } from 'd'");
        // assertThat(mergedContents).contains("private b: number;");
        // assertThat(mergedContents).contains("bProperty");

    }

    /**
     * Tests whether the contents will be rewritten after parsing and printing with the right encoding
     *
     * @throws IOException
     *             test fails
     * @test fails
     */
    @Test
    public void testReadingEncoding() throws IOException {

        File baseFile = new File(testFileRootPath + "baseFile_encoding_UTF-8.py");
        File patchFile = new File(testFileRootPath + "patchFile.py");

        String mergedContents =
            new PythonMerger("pythonmerge", false).merge(baseFile, FileUtils.readFileToString(patchFile), "UTF-8");

        // Use the following assert as template for your testing
        // assertThat(mergedContents.contains("Ñ")).isTrue();

        baseFile = new File(testFileRootPath + "baseFile_encoding_ISO-8859-1.py");

        // Use the following assert as template for your testing
        // assertThat(mergedContents.contains("Ñ")).isTrue();

    }

    /**
     * Reads a python file
     *
     * @param fileName
     *            the python file
     * @return the content of the file
     */
    private String readpythonFile(String fileName) {

        File patchFile = new File(testFileRootPath + fileName);
        String file = patchFile.getAbsolutePath();
        Reader reader = null;
        String returnString;

        try {
            reader = new FileReader(file);
            returnString = IOUtils.toString(reader);
            reader.close();

        } catch (FileNotFoundException e) {
            throw new MergeException(patchFile, "Can not read file " + patchFile.getAbsolutePath());
        } catch (IOException e) {
            throw new MergeException(patchFile, "Can not read the base file " + patchFile.getAbsolutePath());
        }

        return returnString;
    }

}
