/*
 * Copyright (C) 2005-2014 Christian P. Lerch <christian.p.lerch [at] gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kmworks.portal.rpc.client;

import org.kmworks.portal.rpc.client.RPCExtensionsHttp;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.service.http.HttpTestUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author cpl
 */
public class RPCExtensionsHttpTest extends HttpTestUtil {
  
  public RPCExtensionsHttpTest() {
  }

  @Test
  public void testGetFileAsStreamAndBytes() throws Exception {
    long fileEntryId = 11434L;  // use any existing fileEntryId here !!!
    String version = "1.0";
    byte[] buf1 = ByteStreams.toByteArray(RPCExtensionsHttp.getFileAsStream(getToken(), fileEntryId, version));
    byte[] buf2 = RPCExtensionsHttp.getFileAsBytes(getToken(), fileEntryId, version);
    assertTrue(Arrays.equals(buf1, buf2));
  }
  
  @Test
  public void testFileDownload() throws Exception {
    long fileEntryId = 11434L;  // use any existing fileEntryId here !!!
    String version = "1.0";
    String testDir = "src/test/temp/";
    File localFile = new File(testDir + "local-file.tmp");
    File serverDir = new File(testDir);
    File serverFile = new File(serverDir.getAbsoluteFile(), "server-file.tmp");
    long localSize = RPCExtensionsHttp.downloadFile(getToken(), fileEntryId, version, localFile);
    long serverSize = RPCExtensionsHttp.downloadFileToServer(getToken(), fileEntryId, version, serverFile);
    assertEquals(localSize, serverSize);
    assertTrue(Files.equal(localFile, serverFile));
  }

/*  
  @Test
  public void testFileUpload() throws PortalException, SystemException, IOException {
    long folderId = 12810L;   // user any existing folderId in DM repository of test user
    String fileName = "text-page-1.pdf";
    FileEntry fe;
    try (InputStream in = getClass().getResourceAsStream("/" + fileName)) {
      assertTrue(in != null);
      String title = "uploaded-"  + System.currentTimeMillis();
      fe = RPCExtensionsHttp.uploadFile(getToken(), folderId, title, fileName, in);
    }
    System.out.println(fe.getFileEntryId());
  }
*/
  
  @Test
  public void testUsersCount()  throws Exception {
    configure();
    int count = RPCExtensionsHttp.getUsersCount(getToken());
    System.out.println("*** Users count: " + count);
  }
  
  @Test
  public void testGetResources() {
    InputStream in = getClass().getResourceAsStream("/blank-page-1.pdf");
    assertTrue(in != null);
  }
  
  @Test
  public void testRelFile() {
    File relFile = new File("src/test/resources/");
    assertTrue(relFile.isDirectory());
    assertTrue(relFile.canRead());
  }
  
}
