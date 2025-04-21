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
        content += `#level-${i}\n`;

        autoLayerTiles.forEach(tile => {
            
            let id = world.defs.tilesets[0].customData.filter(tilename => {
                return tile.t == tilename.tileId;
            });
            id = id.length > 0 ? id[0].data : 'tile';
            // console.log(id);
            content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},"${id}"\n`;
        });
        tiles.forEach(tile => {
            // content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)}\n`;
            // world.defs.tilesets[0].customData.forEach(tilename => {
            //     if(tile.t == tilename.tileId){
            //         content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},${tilename.data}\n`;
            //     }
            //     else{
            //         content += `${tile.px[0]/32 + 1},${tile.px[1]/32 + 1},${(tile.src[1]/32 + 0) * TILES_IN_ROW + (tile.src[0]/32 + 1)},tile\n`;
            //     }
            // });
        });

        // console.log(content);

        fs.writeFile('./world.level', content, err => {
            if (err) {
                console.error(err);
            }
        });

    });


});



