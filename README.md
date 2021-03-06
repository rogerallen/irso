# irso

An Album of Irrational Songs in Overtone
by Roger Allen

I am going to explore using irrational numbers as a basis for musical
melodies.  I want to use only Overtone to create the audio, so the
source code will be the score.  I like ambient soundscape music and
I'd like to create an album of songs.  

We'll see where this goes.

## Current Status -- November 2012

I think the work-in-progress is good enough to listen to, but it is
not complete.  If you'd like to listen, point your browser to
http://soundcloud.com/roger-allen/sets/irso/

Things I will be looking into include: 

* understanding how volume works.  I don't understand how I can get
  "TOO LOUD" messages from Supercollider, but looking at the sound in
  Audacity shows there is nothing near the max volume in the wav file.
* filling out the songs with higher/lower range notes.  Right now,
  things are a bit flatter than I'd like.
* panning the voices to place them in different places in the stereo
  sound field.
* generally improving the code.  I feel like I'm still in the "write
  the first version to throw away" part.

## Usage

To play a song, use lein run :&lt;song&gt; to listen to the song.  This will
also display a window with an overview of the song's sequences.

To record a song, use lein run :rec-&lt;song&gt;

&lt;song&gt; is one of eso, phiso, piso, sqrt2so, sqrt3so or tauso

Still, if you are interested you should browse the source and play at
the repl.

## License

Copyright (C) 2012 Roger Allen

Source Code is distributed under the Eclipse Public License, the same
as Clojure.

Songs generated by the source code here is distributed under a
[Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
License](http://creativecommons.org/licenses/by-nc-sa/3.0/).

Basically, I want to own the songs that I create with this toolset.
You are free to use this toolset to create *different* songs.
