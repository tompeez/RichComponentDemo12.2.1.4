
import java.sql.Connection
import java.sql.DriverManager
import javax.sql.DataSource
import groovy.sql.Sql

def cli = new CliBuilder( usage: 'groovy queryMSSQL.groovy -h -s sqlserverhost [-P port] -u userid -p password -v value -t textfile queryfile [queryfile]...')
cli.h(longOpt:'help', 'usage information')
cli.s(argName:'servername', longOpt:'server', args:1, required:true, type:GString, 'sqlserverhost')
cli.P(argName:'port', longOpt:'port', args:1, required:false, type:GString, 'port')
cli.u(argName:'userid', longOpt:'userid', args:1, required:true, type:GString, 'userid')
cli.p(argName:'password', longOpt:'password', args:1, required:true, type:GString, 'password')
cli.v(argName:'value', longOpt:'value', args:1, required:true, type:GString, 'value')
cli.t(argName:'textfile', longOpt:'text', args:1, required:true, type:GString, 'text file')
def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()
def port = 1433
if (opt.P)  port = opt.P // If the port was defined
def servername = opt.s
def userid = opt.u
def password = opt.p
def valuetobind = opt.v
def textfile = opt.t
def outFile
def outFileWriter
try {
    outFile = new File(textfile)
    outFile.write("");  // truncate if output file already exists
} catch (Exception e) {
    println "ERROR: Unable to open $textfile for writing";
    return;
}
driver = Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
Connection conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://$servername:$port", userid, password);

try {
    if  (args.length == 0)    {
        usage_error = "Error: Invalid number of arguments"
        usage_error = "\n$usage_error\nUSAGE: groovy queryMSSQL.groovy queryfile\n"
        throw new IllegalArgumentException(usage_error)
    }
    Sql sql = new Sql(conn)
// After options processing the remaining arguments are query files
// Go through the query files one at a time for execution
  for (queryfilename in opt.arguments()) {
        queryfile = new File(queryfilename)
        query = "" // initialize the query string
        param_count = 0      // Number of placeholders needed for parameters to query
        pattern = /\?/ // pattern to look for to find number of parameters
        // read the query from the query file (line by line) and build it
        queryfile.eachLine { it ->
            query += " " + it
        }
      // number of bind variables to satisfy is obtained by number of ? seen in the query
       query.eachMatch(pattern) { param_count++ }
        println '-.' * 40
        println "query is ${query}"

        println "Output is:"
        println '=' * 80
        def count = 0  // row count
        paramlist = []
        if (valuetobind != "none")
            1.upto(param_count) { paramlist << valuetobind }
        sql.eachRow(query, paramlist) { row ->
           count++; // increment number of rows seen so far
           //println "$count. ${row.name}" // print out the column name
           recstr = ""  // initialize the string that represents row
           meta = row.getMetaData() // get metadata about the row

           for (col in 0..<meta.columnCount) {
              // record is stored in a string called recstr
               if (recstr == "") {
                   recstr = row[col]
               }
               else {
                   recstr += "," + row[col]
               }
           }

           outFile.append(recstr + "\n")
        }
    }
    conn.close()
} catch(Exception e) {
    print e.toString()
}
finally {
}