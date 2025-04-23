const fs = require('node:fs');

let content = "";
const TILES_IN_ROW = 24;

fs.readFile('./map.json', 'utf8', (err, data) => {
    if (err) {
        console.error(err);
        return;
    }
    // console.log(data);
    const world = JSON.parse(data);

    // console.log(world.levels);
    world.levels.forEach((level, i) => {
        const autoLayerTiles = level.layerInstances.filter(layer => layer.__identifier === 'IntGrid')[0].autoLayerTiles;
        const tiles = level.layerInstances.filter(layer => layer.__identifier === 'Tiles')[0].gridTiles;
        const entities = level.layerInstances.filter(layer => layer.__identifier === 'Entities')[0].entityInstances;

        const params = level.fieldInstances.filter(param => param.__identifier === 'settings')[0].__value;
        content += `#level-${i},"${params}"\n`;

        autoLayerTiles.forEach(tile => {
            
            let id = world.defs.tilesets[0].customData.filter(tilename => {
                return tile.t == tilename.tileId;
            });
            id = id.length > 0 ? id[0].data : 'tile';
            // console.log(id);
            content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},"${id}"\n`;
        });
        tiles.forEach(tile => {
            let id = world.defs.tilesets[0].customData.filter(tilename => {
                return tile.t == tilename.tileId;
            });
            id = id.length > 0 ? id[0].data : 'tile';
            // console.log(id);
            content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},"${id}"\n`;
        });
        entities.forEach(entity => {
            let destination = entity.__identifier == "MovableSaw" ? entity.fieldInstances.filter(param => param.__identifier === 'dastianation')[0].__value : null;
            let destinationString = destination ? `,${destination.cx},${destination.cy}` : '';
            content += `${entity.px[0]/32 + 1},${entity.px[1]/32 + 1},-1,"${entity.__identifier}"${destinationString}\n`;
        });

        // console.log(content);

        fs.writeFile('./world.level', content, err => {
            if (err) {
                console.error(err);
            }
        });

    });


});



