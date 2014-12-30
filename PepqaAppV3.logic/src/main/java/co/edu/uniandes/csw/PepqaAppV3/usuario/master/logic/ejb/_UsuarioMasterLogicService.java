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

package co.edu.uniandes.csw.PepqaAppV3.usuario.master.logic.ejb;

import co.edu.uniandes.csw.PepqaAppV3.evento.logic.dto.EventoDTO;
import co.edu.uniandes.csw.PepqaAppV3.evento.persistence.api.IEventoPersistence;
import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.dto.UsuarioDTO;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.logic.api._IUsuarioMasterLogicService;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.logic.dto.UsuarioMasterDTO;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.persistence.api.IUsuarioMasterPersistence;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.persistence.entity.UsuariocontactoUsEntity;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.persistence.entity.UsuarioeventosUsEntity;
import co.edu.uniandes.csw.PepqaAppV3.usuario.persistence.api.IUsuarioPersistence;
import java.util.List;
import javax.inject.Inject;

public abstract class _UsuarioMasterLogicService implements _IUsuarioMasterLogicService {

    @Inject
    protected IUsuarioPersistence usuarioPersistance;
    @Inject
    protected IUsuarioMasterPersistence usuarioMasterPersistance;
    @Inject
    protected IEventoPersistence eventoPersistance;

    public UsuarioMasterDTO createMasterUsuario(UsuarioMasterDTO usuario) {
        UsuarioDTO persistedUsuarioDTO = usuarioPersistance.createUsuario(usuario.getUsuarioEntity());
        if (usuario.getCreatecontactoUs() != null) {
            for (UsuarioDTO usuarioDTO : usuario.getCreatecontactoUs()) {
                UsuarioDTO createdUsuarioDTO = usuarioPersistance.createUsuario(usuarioDTO);
                UsuariocontactoUsEntity usuarioUsuarioEntity = new UsuariocontactoUsEntity(persistedUsuarioDTO.getId(), createdUsuarioDTO.getId());
                usuarioMasterPersistance.createUsuariocontactoUsEntity(usuarioUsuarioEntity);
            }
        }
        if (usuario.getCreateeventosUs() != null) {
            for (EventoDTO eventoDTO : usuario.getCreateeventosUs()) {
                EventoDTO createdEventoDTO = eventoPersistance.createEvento(eventoDTO);
                UsuarioeventosUsEntity usuarioEventoEntity = new UsuarioeventosUsEntity(persistedUsuarioDTO.getId(), createdEventoDTO.getId());
                usuarioMasterPersistance.createUsuarioeventosUsEntity(usuarioEventoEntity);
            }
        }
        // update usuario
        if (usuario.getUpdatecontactoUs() != null) {
            for (UsuarioDTO usuarioDTO : usuario.getUpdatecontactoUs()) {
                usuarioPersistance.updateUsuario(usuarioDTO);
                UsuariocontactoUsEntity usuarioUsuarioEntity = new UsuariocontactoUsEntity(persistedUsuarioDTO.getId(), usuarioDTO.getId());
                usuarioMasterPersistance.createUsuariocontactoUsEntity(usuarioUsuarioEntity);
            }
        }
        // update evento
        if (usuario.getUpdateeventosUs() != null) {
            for (EventoDTO eventoDTO : usuario.getUpdateeventosUs()) {
                eventoPersistance.updateEvento(eventoDTO);
                UsuarioeventosUsEntity usuarioEventoEntity = new UsuarioeventosUsEntity(persistedUsuarioDTO.getId(), eventoDTO.getId());
                usuarioMasterPersistance.createUsuarioeventosUsEntity(usuarioEventoEntity);
            }
        }
        return usuario;
    }

    public UsuarioMasterDTO getMasterUsuario(Long id) {
        return usuarioMasterPersistance.getUsuario(id);
    }

    public void deleteMasterUsuario(Long id) {
        usuarioPersistance.deleteUsuario(id);
    }
    
    public boolean crearRelacion(String us1, String us2) {
        boolean norepetido = true;
        List<UsuarioDTO> todosLosUsuarios = usuarioPersistance.getUsuarios();
        long idUs1 = 0L;
        long idUs2 = 0L;
        for (int i = 0; i < todosLosUsuarios.size(); i++) {
            UsuarioDTO act = todosLosUsuarios.get(i);
            if(act.getName().equals(us1))
            {
                idUs1=act.getId();
            }
            else if(act.getName().equals(us2))
            {
                idUs2=act.getId();
            }
        }
        List<UsuariocontactoUsEntity> relaciones = usuarioPersistance.getRelaciones();
        for(int i = 0;i<relaciones.size();i++)
        {
            UsuariocontactoUsEntity act = relaciones.get(i);
            if(idUs1==act.getContactoUsId() && idUs2==act.getUsuarioId())
            {
                norepetido = false;
            }
        }
        if(norepetido)
        {
        UsuariocontactoUsEntity usuarioUsuarioEntity1 = new UsuariocontactoUsEntity(idUs1,idUs2);
        UsuariocontactoUsEntity usuarioUsuarioEntity2 = new UsuariocontactoUsEntity(idUs2,idUs1);
        usuarioMasterPersistance.createUsuariocontactoUsEntity(usuarioUsuarioEntity1);
        usuarioMasterPersistance.createUsuariocontactoUsEntity(usuarioUsuarioEntity2);}
        return norepetido;
    }

    public void updateMasterUsuario(UsuarioMasterDTO usuario) {
        usuarioPersistance.updateUsuario(usuario.getUsuarioEntity());

        //---- FOR RELATIONSHIP
        // delete usuario
        if (usuario.getDeletecontactoUs() != null) {
            for (UsuarioDTO usuarioDTO : usuario.getDeletecontactoUs()) {
                usuarioMasterPersistance.deleteUsuariocontactoUsEntity(usuario.getUsuarioEntity().getId(), usuarioDTO.getId());
            }
        }
        // persist new usuario
        if (usuario.getCreatecontactoUs() != null) {
            for (UsuarioDTO usuarioDTO : usuario.getCreatecontactoUs()) {
                UsuariocontactoUsEntity usuarioUsuarioEntity = new UsuariocontactoUsEntity(usuario.getUsuarioEntity().getId(), usuarioDTO.getId());
                usuarioMasterPersistance.createUsuariocontactoUsEntity(usuarioUsuarioEntity);
            }
        }
        // update usuario
        if (usuario.getUpdatecontactoUs() != null) {
            for (UsuarioDTO usuarioDTO : usuario.getUpdatecontactoUs()) {
                usuarioMasterPersistance.deleteUsuariocontactoUsEntity(usuario.getUsuarioEntity().getId(), usuarioDTO.getId());
                usuarioPersistance.updateUsuario(usuarioDTO);
                UsuariocontactoUsEntity usuarioUsuarioEntity = new UsuariocontactoUsEntity(usuario.getId(), usuarioDTO.getId());
                usuarioMasterPersistance.createUsuariocontactoUsEntity(usuarioUsuarioEntity);
                
            }
        }
        // delete evento
        if (usuario.getDeleteeventosUs() != null) {
            for (EventoDTO eventoDTO : usuario.getDeleteeventosUs()) {
                usuarioMasterPersistance.deleteUsuarioeventosUsEntity(usuario.getUsuarioEntity().getId(), eventoDTO.getId());
            }
        }
        // persist new evento
        if (usuario.getCreateeventosUs() != null) {
            for (EventoDTO eventoDTO : usuario.getCreateeventosUs()) {
                UsuarioeventosUsEntity usuarioEventoEntity = new UsuarioeventosUsEntity(usuario.getUsuarioEntity().getId(), eventoDTO.getId());
                usuarioMasterPersistance.createUsuarioeventosUsEntity(usuarioEventoEntity);
            }
        }
        // update evento
        if (usuario.getUpdateeventosUs() != null) {
            for (EventoDTO eventoDTO : usuario.getUpdateeventosUs()) {
                usuarioMasterPersistance.deleteUsuarioeventosUsEntity(usuario.getUsuarioEntity().getId(), eventoDTO.getId());
                eventoPersistance.updateEvento(eventoDTO);
                UsuarioeventosUsEntity usuarioEventoEntity = new UsuarioeventosUsEntity(usuario.getId(), eventoDTO.getId());
                usuarioMasterPersistance.createUsuarioeventosUsEntity(usuarioEventoEntity);
                
            }
        }
    }
}
