package lab5.client.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Operates input and output.
 */
public class IOManager {
    private BufferedReader reader;
    private PrintWriter writer;
    private final String prompter;

    public IOManager(BufferedReader reader, PrintWriter writer, String promter){
        this.reader = reader;
        this.writer = writer;
        this.prompter = promter;
    }

    /**
     * Set BufferedReader.
     * @param buf BufferedReader.
     */
    public  void setBufferReader(BufferedReader buf) {
        reader = buf;
    }

    /**
     * Set PrintWriter.
     * @param wr PrintWriter.
     */
    public  void setPrintWriter(PrintWriter wr) {
        writer = wr;
    }

    /**
     * @return BufferedReader.
     */
    public  BufferedReader getBufferedReader() {
        return reader;
    }

    /**
     * @return PrintWriter.
     */
    public  PrintWriter getPrintWriter() {
        return writer;
    }

    /**
     * Read new line from input.
     * @return line.
     * @throws IOException If something goes wrong with file.
     */
    public  String readLine() throws IOException {
        return reader.readLine();
    }

    /**
     * Read file.
     * @return lines of file.
     * @throws IOException If something goes wrong with file.
     */
    public String readfile(File file) throws FileNotFoundException, IOException{
        StringBuilder strData = new StringBuilder();
        String line;
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                while ((line = bufferedReader.readLine()) != null) {
                    strData.append(line);
                }
            }
        }
        return strData.toString();
    }

    /**
     * Close input.
     * @throws IOException If something goes wrong with file.
     */
    public  void close() throws IOException {
        reader.close();
    }

    /**
     * Print prompt for input.
     */
    public void prompt(){
        writer.printf("%s", prompter);
    }

    /**
     * Print in output.
     * @param o Object to print.
     */
    public  void print(Object o) {
        writer.printf("%s", o);
    }

    /**
     * Print with new line in output.
     * @param o Object to print.
     */
    public  void println(Object o) {
        writer.println(o);
    }

    /**
     * Print errors in output.
     * @param o Object to print.
     */
    public  void printerr(Object o) {
        writer.println("Error! " + o);
    }

    /**
     * Print elements as table.
     * @param el1 element of the first column.
     * @param el2 element of the second column.
     */
    public  void printcolon(Object el1, Object el2) {
        writer.printf("%-25s%-1s%n", el1, el2);
    }
}