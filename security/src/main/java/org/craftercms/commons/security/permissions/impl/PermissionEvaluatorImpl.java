/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
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
package org.craftercms.commons.security.permissions.impl;

import org.craftercms.commons.security.exception.PermissionException;
import org.craftercms.commons.security.permissions.*;

/**
 * Default implementation of {@link org.craftercms.commons.security.permissions.PermissionEvaluator}
 *
 * @author avasquez
 */
public class PermissionEvaluatorImpl<S, O> implements PermissionEvaluator<S, O> {

    protected SubjectResolver<S> subjectResolver;
    protected PermissionResolver<S, O> permissionResolver;
    protected ParentResolver<O> parentResolver;

    public void setPermissionResolver(PermissionResolver<S, O> permissionResolver) {
        this.permissionResolver = permissionResolver;
    }

    public void setParentResolver(ParentResolver<O> parentResolver) {
        this.parentResolver = parentResolver;
    }

    @Override
    public boolean isAllowed(O object, String action) throws PermissionException {
        if (subjectResolver == null) {
            throw new PermissionException("No SubjectResolver found. Unable to infer current subject");
        }

        return isAllowed(subjectResolver.getCurrentSubject(), object, action);
    }

    @Override
    public boolean isAllowed(S subject, O object, String action) throws PermissionException {
        Permission permission;

        if (object == null) {
            permission = permissionResolver.getGlobalPermission(subject);
        } else {
            permission = permissionResolver.getPermission(subject, object);
        }

        if (permission != null) {
            return permission.isAllowed(action);
        } else if (parentResolver != null) {
            Object parent = parentResolver.getParent(object);
            if (parent != null) {
                return isAllowed(subject, object, action);
            }
        }

        return false;
    }

}