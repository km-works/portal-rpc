/*
 * Copyright (C) 2005-2016 Christian P. Lerch <christian.p.lerch [at] gmail.com>
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
package org.kmworks.portal.rpc.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author cpl
 */
public abstract class SerializableInputStream implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private transient InputStream in;
  private final String streamToken;
  
  
  protected SerializableInputStream(String streamToken) {
    this.streamToken = streamToken;
  }

  
  protected abstract InputStream reestablishInputStream(String token);
  
  
  /* 
      Delegated InputStream methods 
  */
  public int read() throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    return in.read();
  }

  public int read(byte[] b) throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    return in.read(b);
  }

  public int read(byte[] b, int off, int len) throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    return in.read(b, off, len);
  }

  public long skip(long n) throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    return in.skip(n);
  }

  public int available() throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    return in.available();
  }

  public void close() throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    in.close();
  }

  public synchronized void mark(int readlimit) {
    if (in == null) in = reestablishInputStream(streamToken);
    in.mark(readlimit);
  }

  public synchronized void reset() throws IOException {
    if (in == null) in = reestablishInputStream(streamToken);
    in.reset();
  }

  public boolean markSupported() {
    if (in == null) in = reestablishInputStream(streamToken);
    return in.markSupported();
  }
  
  /*
      General overrides
  */

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + Objects.hashCode(this.streamToken);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SerializableInputStream other = (SerializableInputStream) obj;
    if (!Objects.equals(this.streamToken, other.streamToken)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SerializableInputStream{" + "token=" + streamToken + '}';
  }

}
