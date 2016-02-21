package fileSearcher

object Cli extends App {
  def argsToMather(args: Array[String]): Matcher = args match {
    case Array(filter) => new Matcher(filter)
    case Array(filter, path) => new Matcher(filter, path)
    case Array(filter, path, contentFilter) => new Matcher(filter, path, Option(contentFilter))
    case Array(filter, path, contentFilter, maxFileSize, _*) => new Matcher(filter, path, Option(contentFilter), maxFileSize.toDouble)
  }

  if (args.length == 0) {
    System.err.print("Usage:\n\tfile_searcher file_name_pattern [root_path] [file_content_filter] [output_file_path] [max_file_size]\n")
    System.exit(1)
  }

  val matcher = argsToMather(args)
  val searchResults = matcher.execute()

  args match {
    case Array(_, _, _, outputFilePath, _*) =>
      SearchResultWriter.writeToFile(outputFilePath, searchResults)
    case _ =>
      SearchResultWriter.writeToConsole(searchResults)
  }
}
