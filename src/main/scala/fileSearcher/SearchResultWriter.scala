package fileSearcher

import java.io.PrintWriter

object SearchResultWriter {
  private def formattedResult(result: (String, Option[Int])): String = result match {
    case (fileName, Some(number)) => s"$fileName:$number"
    case (fileName, None)         => fileName
  }

  private def joinedLines(searchResults: List[(String, Option[Int])]) = searchResults.foldLeft("")(
    (str, result) => str + formattedResult(result) + "\n")

  def writeToConsole(searchResults: List[(String, Option[Int])]): Unit = {
    { System.out.print(joinedLines(searchResults)) }
  }

  def writeToFile(filePath: String, searchResults: List[(String, Option[Int])]): Unit = {
    {
      new PrintWriter(filePath) {
        write(joinedLines(searchResults))
        close()
      }
    }
  }
}
