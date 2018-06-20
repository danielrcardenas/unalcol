package unalcol.types.object.tagged;

import unalcol.types.collection.Collection;
import unalcol.types.collection.keymap.HTKeyMap;
import unalcol.types.collection.keymap.KeyMap;
import unalcol.types.collection.keymap.KeyValue;

//
//Unalcol Service structure Pack 1.0 by Jonatan Gomez-Perdomo
//https://github.com/jgomezpe/unalcol/tree/master/core/
//
/**
*
* Tagged<T>
* <p>An object of type T that has some associated tags.</p>
*
* <P>
* <A HREF="https://github.com/jgomezpe/unalcol/tree/master/core/src/unalcol/Tagged.java" target="_blank">
* Source code </A> is available.
*
* <h3>License</h3>
*
* Copyright (c) 2014 by Jonatan Gomez-Perdomo. <br>
* All rights reserved. <br>
*
* <p>Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
* <ul>
* <li> Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
* <li> Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
* <li> Neither the name of the copyright owners, their employers, nor the
* names of its contributors may be used to endorse or promote products
* derived from this software without specific prior written permission.
* </ul>
* <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*
*
*
* @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
* (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
* @version 1.0
*/
public class Tagged<T>{
	protected T object;
	protected KeyMap<Object, Object> tags = new HTKeyMap<Object,Object>();
	
	public Tagged( T object ){ this.object = object; }
	

	public Tagged( T object, KeyMap<Object, Object> tags ){
		this.object = object;
		for(KeyValue<Object,Object> k:tags.pairs()) tags.set(k);
	}
 
	/**
	 * Sets the object that is being tagged. Removes all the non TaggedMethods associated to this object.
	 * @param object Object that is being tagged.
	 */
	public void wrap( T object ){ this.object = object; }
	
	/**
	 * Gets the object that is being tagged.
	 * @return tagged object.
	 */
	public T unwrap(){ return object; }	
	
	public Object getTag( Object tag ){ return tags.get(tag); };
	public void setTag( Object tag, Object value ){ tags.set(tag, value); }
	public void removeTag( Object tag ){ tags.remove(tag); }
	public Collection<Object> tags(){ return tags.keys(); }
}