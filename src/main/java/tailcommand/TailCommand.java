package tailcommand;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TailCommand {

    private final String fileName;
    private final int numberOfLines;

    public TailCommand(String fileName, int numberOfLines) {
        if(fileName == null || fileName.isEmpty() || numberOfLines <= 0){
            throw new IllegalStateException(
                    String.format("Invalid [fileName:%s], [numberOfLines:%s] ", fileName, numberOfLines));
        }
        this.fileName = fileName;
        this.numberOfLines = numberOfLines;
    }

    @Override
    public String toString() {
        return "TailCommand{" +
                "fileName='" + fileName + '\'' +
                ", numberOfLines=" + numberOfLines +
                '}';
    }

    public String tail() throws IOException {
        try(RandomAccessFile filePtr = new RandomAccessFile(fileName, "r")){
            final long fileSize = filePtr.length();
            filePtr.seek(fileSize - 1); // move the file pointer to the end of the file

            long newLineCount = 1L;
            StringBuilder lastNLines = new StringBuilder();

            // read file in reverse and look for line separator(s) like \n or any other separator used by OS
            for(long lineNum = fileSize - 1; lineNum != -1; lineNum--){
                filePtr.seek(lineNum); // move the pointer to the current lineNum
                final int readByte = filePtr.readByte();
                final char ch = (char) readByte; // convert by to char
                if(ch == '\n' || System.lineSeparator().equals(String.valueOf(ch))){
                    newLineCount++;
                    if(newLineCount > numberOfLines){
                        break;
                    }
                }
                lastNLines.append(ch);
            }
            lastNLines.reverse();
            return lastNLines.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "testfile.txt";
        TailCommand tailCommand1 = new TailCommand(fileName, 2); // last 2 lines
        System.out.println(tailCommand1.tail());
        System.out.println("----------------------------------------------");
        TailCommand tailCommand2 = new TailCommand(fileName, 5); // last 5 lines
        System.out.println(tailCommand2.tail());
        System.out.println("----------------------------------------------");
        TailCommand tailCommand3 = new TailCommand(fileName, 15); // all 10 lines we are tailing more lines that are available in the file
        System.out.println(tailCommand3.tail());
        System.out.println("----------------------------------------------");
    }
}
