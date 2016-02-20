package fileSearcher

import java.io.PrintWriter

object SearchResultWriter {
  def writeToFile(filePath: String, searchResults: List[(String, Option[Int])]): Unit = {
    def formattedResult(result: (String, Option[Int])): String = result match {
      case (fileName, Some(number)) => s"$fileName:$number"
      case (fileName, None)         => fileName
    }

    def joinedLines = searchResults.foldLeft("")(
      (str, result) => str + formattedResult(result) + "\n")

    {
      new PrintWriter(filePath) {
        write(joinedLines)
        close()
      }
    }
  }
}
