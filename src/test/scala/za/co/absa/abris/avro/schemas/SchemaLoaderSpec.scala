/*
 * Copyright 2018 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.absa.abris.avro.schemas

import java.io.File

import org.apache.commons.io.FileUtils
import org.scalatest.FlatSpec
import za.co.absa.abris.examples.data.generation.TestSchemas

class SchemaLoaderSpec extends FlatSpec {

  private val testDir = new File("testDirSchemaLoader")

  behavior of "SchemaLoader"

  it should "retrieve schemas from file systems" in {
    val expectedSchema = TestSchemas.COMPLEX_SCHEMA_SPEC
    val schemaFileName = "testSchemaName"
    val destination = writeIntoFS(expectedSchema, schemaFileName)
    val loadedSchema = SchemaLoader.loadFromFile(destination.getAbsolutePath)

    FileUtils.deleteQuietly(new File(destination.getAbsolutePath))
    FileUtils.deleteDirectory(testDir)

    assert(expectedSchema == loadedSchema)
  }

  private def writeIntoFS(schema: String, name: String): File = {
    val destination = new File(testDir, name)
    FileUtils.write(destination, schema)
    destination
  }
}
