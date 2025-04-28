// World level converter from LDtk application
// This scripts needs to parce the map.json file and convert it to a world.level file


const fs = require('node:fs');

let content = "";
const TILES_IN_ROW = 24;

fs.readFile('./map.json', 'utf8', (err, data) => {
    if (err) {
        console.error(err);
        return;
    }
    
    const world = JSON.parse(data);


    world.levels.forEach((level, i) => {
        // Auto-generated platfroms
        const autoLayerTiles = level.layerInstances.filter(layer => layer.__identifier === 'IntGrid')[0].autoLayerTiles;

        // Tiles
        const tiles = level.layerInstances.filter(layer => layer.__identifier === 'Tiles')[0].gridTiles;
        
        // All saws, power upps, and other entities
        const entities = level.layerInstances.filter(layer => layer.__identifier === 'Entities')[0].entityInstances;

        // Back and front palms
        const palms = level.layerInstances.filter(layer => layer.__identifier === 'Palms')[0].entityInstances;

        // Level parameters
        const params = level.fieldInstances.filter(param => param.__identifier === 'settings')[0].__value;

        // Add new level line
        content += `#level-${i},"${params}"\n`;

        autoLayerTiles.forEach(tile => {
            
            let id = world.defs.tilesets[0].customData.filter(tilename => {
                return tile.t == tilename.tileId;
            });
            id = id.length > 0 ? id[0].data : 'tile';
            content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},"${id}"\n`;
        });

        tiles.forEach(tile => {
            let id = world.defs.tilesets[0].customData.filter(tilename => {
                return tile.t == tilename.tileId;
            });
            id = id.length > 0 ? id[0].data : 'tile';
            content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},"${id}"\n`;
        });
        entities.forEach(entity => {
            let destination = entity.__identifier == "MovableSaw" ? entity.fieldInstances.filter(param => param.__identifier === 'dastianation')[0].__value : null;
            let destinationString = destination ? `,${destination.cx},${destination.cy}` : '';
            content += `${entity.px[0]/32 + 1},${entity.px[1]/32 + 1},-1,"${entity.__identifier}"${destinationString}\n`;
        });

        palms.forEach(palm => {
            let palmIncrement = 1;

            if (palm.__identifier == "FrontPalm1") {
                palmIncrement = palm.width / 39;
            } else if (palm.__identifier == "FrontPalm2") {
                palmIncrement = palm.width / 78;
            } else if (palm.__identifier == "FrontPalm3") {
                palmIncrement = palm.width / 39;
            } else if (palm.__identifier == "BackPalm1") {
                palmIncrement = palm.width / 64;
            } else if (palm.__identifier == "BackPalm2") {
                palmIncrement = palm.width / 51;
             }else if (palm.__identifier == "BackPalm3") {
                palmIncrement = palm.width / 52;
            }
            content += `${palm.px[0]/32 + 1},${palm.px[1]/32 + 1},-2,"${palm.__identifier}",${palmIncrement}\n`;
        });

        fs.writeFile('./world.level', content, err => {
            if (err) {
                console.error(err);
            }
        });

    });


});



