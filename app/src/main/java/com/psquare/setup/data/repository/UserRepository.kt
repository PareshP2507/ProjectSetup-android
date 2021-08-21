package com.psquare.setup.data.repository

import com.psquare.setup.domain.IUserRepository
import com.psquare.setup.data.source.UserService

class UserRepository(userService: UserService) : IUserRepository {
}