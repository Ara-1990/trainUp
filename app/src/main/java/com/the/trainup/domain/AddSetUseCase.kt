package com.the.trainup.domain


class AddSetUseCase(private val repo: SetRepository) {
    suspend operator fun invoke(entry: SetEntry) = repo.addSet(entry)
}
