package com.andrewward2001.kml2json

import org.apache.commons.lang3.StringUtils
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    var inputStream: InputStream = File("res/zbw.kml").inputStream()
    if(!args.isEmpty() && args.get(0) != null) run {
        inputStream = File(args[0]).inputStream()
    }
    val inputString = inputStream.bufferedReader().use { it.readText() }

    var finalOutputString = "{\n  \"polygons\": [\n"

    // array of all occurrences of the <Placemark> tag
    var polys = StringUtils.substringsBetween(inputString, "<Placemark>", "</Placemark>")
    for(i in 0 until polys.size) {
        var place = polys[i]
        // ensures that the given Placemark is a polygon before continuing
        if(!place.contains("<Polygon>"))
            return

        var thisPolyString = "    {\n"

        // name of the Placemark, which is a Polygon
        val name = StringUtils.substringBetween(place, "<name>", "</name>")
        thisPolyString += "      \"name\": \"$name\",\n"

        // creates a substring containing just the coordinate triplets of the polygon
        var coords = StringUtils.substringBetween(place, "<coordinates>", "</coordinates>")
        // splits the single string of many coordinate triplets into an array of individual triplets
        var coordTriplets: List<String> = coords.split(" ")
        // creates a hashmap which will store coordinate pairs
        var coordArray: LinkedHashMap<Double, Double> = LinkedHashMap()
        // create a final string which will also contain each coordinate pair
        var coordPairString = String()

        for(j in 0 until coordTriplets.size - 1) {
            // trims and splits coordinate triplets by their comma separators
            var coordPairs = coordTriplets[j].trim().split(",")
            // insert coordinate pairs into the hashmap
            coordArray.put(coordPairs[0].toDouble(), coordPairs[1].toDouble())

            if(j != 0)
                coordPairString += ",\n"
            coordPairString += "        [" + coordPairs[0] + ", " + coordPairs[1] + "]"
        }

        thisPolyString += "      \"points\": [\n$coordPairString\n      ]\n    }"
        if(i != 0)
            finalOutputString += ",\n"
        finalOutputString += thisPolyString
    }

    finalOutputString += "\n  ]\n}"
    println(finalOutputString)

    var outputLines = finalOutputString.split("\\r?\\n")
    var file = Paths.get("kml2json-output.json")
    Files.write(file, outputLines, Charset.forName("UTF-8"))

}
