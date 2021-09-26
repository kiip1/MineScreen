# MineScreen

[![license](https://img.shields.io/github/license/kiipy/MineScreen?style=for-the-badge&color=dd7744)](./LICENSE)
[![wiki](https://img.shields.io/badge/documentation-wiki-x?style=for-the-badge&color=cc7788)](https://github.com/kiipy/MineScreen/wiki)

Create Minestom GUIs using an easy to use api.

# Installing
```groovy
repositories {
    mavenCentral()

    // Minestom repos
    maven { url 'https://repo.spongepowered.org/maven' }
    maven { url 'https://jitpack.io' }

    // Github release
    ivy {
        url 'https://github.com/'

        patternLayout {
            artifact '/[organisation]/[module]/releases/download/latest/[revision].jar'
        }

        metadataSources { artifact() }
    }
}

dependencies {
    // Minestom (new block api)
    implementation 'com.github.Minestom:Minestom:872a49d'

    // MineScreen
    implementation 'kiipy:MineScreen:MineScreen-1.0.1-dev'
}
```

# Documentation
MineScreen is poorly documented right now, but this will be improved in the future.

# Contributing
You are always welcome to contribute.

# TODO
- [x] Basic gui support.
- [ ] Hunt for bugs and fix them.
- [ ] Center the gui to the center of the screen.
- [ ] Improve performance of MapGraphics. 
- [ ] Add more components.
- [ ] Add more methods to MapGraphics.
