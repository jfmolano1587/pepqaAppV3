/* ========================================================================
 * Copyright 2014 PepqaAppV3
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 PepqaAppV3

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * ========================================================================


Source generated by CrudMaker version 1.0.0.201411201032

*/

package co.edu.uniandes.csw.PepqaAppV3.pepqaapp.master.logic.dto;

import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.dto.UsuarioDTO;
import co.edu.uniandes.csw.PepqaAppV3.evento.logic.dto.EventoDTO;
import co.edu.uniandes.csw.PepqaAppV3.pepqaapp.logic.dto.PepqaAppDTO;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class _PepqaAppMasterDTO {

 
    protected PepqaAppDTO pepqaappEntity;
    protected Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public PepqaAppDTO getPepqaAppEntity() {
        return pepqaappEntity;
    }

    public void setPepqaAppEntity(PepqaAppDTO pepqaappEntity) {
        this.pepqaappEntity = pepqaappEntity;
    }
    
    public List<UsuarioDTO> createusuarios;
    public List<UsuarioDTO> updateusuarios;
    public List<UsuarioDTO> deleteusuarios;
    public List<UsuarioDTO> listusuarios;	
    public List<EventoDTO> createeventos;
    public List<EventoDTO> updateeventos;
    public List<EventoDTO> deleteeventos;
    public List<EventoDTO> listeventos;	
	
	
	
    public List<UsuarioDTO> getCreateusuarios(){ return createusuarios; };
    public void setCreateusuarios(List<UsuarioDTO> createusuarios){ this.createusuarios=createusuarios; };
    public List<UsuarioDTO> getUpdateusuarios(){ return updateusuarios; };
    public void setUpdateusuarios(List<UsuarioDTO> updateusuarios){ this.updateusuarios=updateusuarios; };
    public List<UsuarioDTO> getDeleteusuarios(){ return deleteusuarios; };
    public void setDeleteusuarios(List<UsuarioDTO> deleteusuarios){ this.deleteusuarios=deleteusuarios; };
    public List<UsuarioDTO> getListusuarios(){ return listusuarios; };
    public void setListusuarios(List<UsuarioDTO> listusuarios){ this.listusuarios=listusuarios; };	
    public List<EventoDTO> getCreateeventos(){ return createeventos; };
    public void setCreateeventos(List<EventoDTO> createeventos){ this.createeventos=createeventos; };
    public List<EventoDTO> getUpdateeventos(){ return updateeventos; };
    public void setUpdateeventos(List<EventoDTO> updateeventos){ this.updateeventos=updateeventos; };
    public List<EventoDTO> getDeleteeventos(){ return deleteeventos; };
    public void setDeleteeventos(List<EventoDTO> deleteeventos){ this.deleteeventos=deleteeventos; };
    public List<EventoDTO> getListeventos(){ return listeventos; };
    public void setListeventos(List<EventoDTO> listeventos){ this.listeventos=listeventos; };	
	
	
}

