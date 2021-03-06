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

package co.edu.uniandes.csw.PepqaAppV3.usuario.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.dto.UsuarioDTO;
import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.api.IUsuarioLogicService;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.logic.api._IUsuarioMasterLogicService;
import co.edu.uniandes.csw.PepqaAppV3.usuario.master.logic.dto.UsuarioMasterDTO;
import co.edu.uniandes.csw.PepqaAppV3.evento.logic.api.IEventoLogicService;
import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.api.IUsuarioLogicService;
import co.edu.uniandes.csw.PepqaAppV3.evento.logic.dto.EventoDTO;
import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.dto.UsuarioDTO;
import javax.inject.Inject;


public abstract class _UsuarioMasterMockLogicService implements _IUsuarioMasterLogicService {

    protected static ArrayList<UsuarioMasterDTO> usuarioMasterDtosList = new ArrayList<UsuarioMasterDTO>() ;
    @Inject
    protected IEventoLogicService eventoPersistance;
    @Inject
    protected IUsuarioLogicService usuarioPersistance;

    public UsuarioMasterDTO createMasterUsuario(UsuarioMasterDTO usuario) {

        usuarioPersistance.createUsuario(usuario.getUsuarioEntity());
        for (UsuarioDTO dto : usuario.getCreatecontactoUs()) {
            usuario.getListcontactoUs().add(usuarioPersistance.createUsuario(dto));
        }
        for (EventoDTO dto : usuario.getCreateeventosUs()) {
            usuario.getListeventosUs().add(eventoPersistance.createEvento(dto));
        }
        usuarioMasterDtosList.add(usuario);
        return usuario;
    }

    public UsuarioMasterDTO getMasterUsuario(Long id) {
        for (UsuarioMasterDTO usuarioMasterDTO : usuarioMasterDtosList) {
            if (usuarioMasterDTO.getUsuarioEntity().getId() == id) {
                return usuarioMasterDTO;
            }
        }

        return null;
    }

    public void deleteMasterUsuario(Long id) {
        for (UsuarioMasterDTO usuarioMasterDTO : usuarioMasterDtosList) {
            if (usuarioMasterDTO.getUsuarioEntity().getId() == id) {

                for (UsuarioDTO dto : usuarioMasterDTO.getCreatecontactoUs()) {
                    usuarioPersistance.deleteUsuario(dto.getId());
                }
                usuarioPersistance.deleteUsuario(usuarioMasterDTO.getId());
                usuarioMasterDtosList.remove(usuarioMasterDTO);
                for (EventoDTO dto : usuarioMasterDTO.getCreateeventosUs()) {
                    eventoPersistance.deleteEvento(dto.getId());
                }
                usuarioPersistance.deleteUsuario(usuarioMasterDTO.getId());
                usuarioMasterDtosList.remove(usuarioMasterDTO);
            }
        }

    }

    public void updateMasterUsuario(UsuarioMasterDTO usuario) {

		UsuarioMasterDTO currentUsuario = getMasterUsuario(usuario.getUsuarioEntity().getId());
		if (currentUsuario == null) {
			currentUsuario = usuario;
		}else{
			usuarioMasterDtosList.remove(currentUsuario);
		}

        // update Usuario
        if (usuario.getUpdatecontactoUs() != null) {
            for (UsuarioDTO dto : usuario.getUpdatecontactoUs()) {
                usuarioPersistance.updateUsuario(dto);
                for (UsuarioDTO usuariodto : currentUsuario.getListcontactoUs()) {
					if (usuariodto.getId() == dto.getId()) {
						currentUsuario.getListcontactoUs().remove(usuariodto);
						currentUsuario.getListcontactoUs().add(dto);
					}
				}
            }
        }
        // persist new Usuario
        if (usuario.getCreatecontactoUs() != null) {
            for (UsuarioDTO dto : usuario.getCreatecontactoUs()) {
                UsuarioDTO persistedUsuarioDTO = usuarioPersistance.createUsuario(dto);
                dto = persistedUsuarioDTO;
                currentUsuario.getListcontactoUs().add(dto);
            }
        }
        // delete Usuario
        if (usuario.getDeletecontactoUs() != null) {
            for (UsuarioDTO dto : usuario.getDeletecontactoUs()) {
				for (UsuarioDTO usuariodto : currentUsuario.getListcontactoUs()) {
					if (usuariodto.getId() == dto.getId()) {
						currentUsuario.getListcontactoUs().remove(usuariodto);
					}
				}
                usuarioPersistance.deleteUsuario(dto.getId());
            }
        }
        // update Evento
        if (usuario.getUpdateeventosUs() != null) {
            for (EventoDTO dto : usuario.getUpdateeventosUs()) {
                eventoPersistance.updateEvento(dto);
                for (EventoDTO eventodto : currentUsuario.getListeventosUs()) {
					if (eventodto.getId() == dto.getId()) {
						currentUsuario.getListeventosUs().remove(eventodto);
						currentUsuario.getListeventosUs().add(dto);
					}
				}
            }
        }
        // persist new Evento
        if (usuario.getCreateeventosUs() != null) {
            for (EventoDTO dto : usuario.getCreateeventosUs()) {
                EventoDTO persistedEventoDTO = eventoPersistance.createEvento(dto);
                dto = persistedEventoDTO;
                currentUsuario.getListeventosUs().add(dto);
            }
        }
        // delete Evento
        if (usuario.getDeleteeventosUs() != null) {
            for (EventoDTO dto : usuario.getDeleteeventosUs()) {
				for (EventoDTO eventodto : currentUsuario.getListeventosUs()) {
					if (eventodto.getId() == dto.getId()) {
						currentUsuario.getListeventosUs().remove(eventodto);
					}
				}
                eventoPersistance.deleteEvento(dto.getId());
            }
        }
        usuarioMasterDtosList.add(currentUsuario);
    }
}