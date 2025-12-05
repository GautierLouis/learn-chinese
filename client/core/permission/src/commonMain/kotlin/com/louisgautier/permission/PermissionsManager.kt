package com.louisgautier.permission

import com.louisgautier.utils.CoreKeepForR8


/**
 * Manages platform-specific permissions.
 *
 * This interface provides a way to check if a permission is granted and to request a permission.
 */
expect class PermissionsManager {
    fun isPermissionGranted(permission: PermissionType): PermissionResult
    suspend fun requestPermission(permission: PermissionType, callback: PermissionCallback)
}

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionResult)
}

@CoreKeepForR8
enum class PermissionResult {
    GRANTED,
    DENIED,
}

@CoreKeepForR8
enum class PermissionType {
    GALLERY,
    LOCALISATION,
    NOTIFICATION
}